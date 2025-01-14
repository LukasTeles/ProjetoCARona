package com.br.uepb.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.UsuarioDAO;
import com.br.uepb.dao.hibernateUtil.HibernateUtil;
import com.br.uepb.domain.PerfilDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

@Service
public class UsuarioDAOImpl implements UsuarioDAO {

	private static UsuarioDAOImpl usuarioDAOImpl;
	
	final static Logger logger = Logger.getLogger(UsuarioDAOImpl.class);
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session;
	private Transaction transaction;
	private Criteria criteria;
	
	//Lista de usuários
	ArrayList<UsuarioDomain> listaUsuarios = new ArrayList<UsuarioDomain>();
	
	//Singleton para UsuarioDAOImpl
	public static UsuarioDAOImpl getInstance(){
		if(usuarioDAOImpl == null){
			usuarioDAOImpl = new UsuarioDAOImpl();
		}
		return usuarioDAOImpl;		
	}
	
	private UsuarioDAOImpl() {}
	
	@Override
	public UsuarioDomain getUsuario(String login) throws Exception {
		if ( (login == null) || (login.trim().equals("")) ){
			logger.debug("getUsuario() Exceção: "+MensagensErro.LOGIN_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.LOGIN_INVALIDO);
		}
		
		UsuarioDomain usuario;
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(UsuarioDomain.class);
			criteria.add(Restrictions.eq("login", login));
			usuario = (UsuarioDomain) criteria.uniqueResult();
			session.close();
			
		}catch(Exception e){
			throw e;
		}
		
		if(usuario != null){
			return usuario;
		}else{
			logger.debug("getUsuario() Exceção: "+MensagensErro.USUARIO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.USUARIO_INEXISTENTE);
		}
	}

	@Override
	public void addUsuario(UsuarioDomain usuario) throws Exception{
		if (loginExiste(usuario.getLogin())) {	
			logger.debug("addUsuario() Exceção: "+MensagensErro.USUARIO_JA_EXISTE);
			throw new ProjetoCaronaException(MensagensErro.USUARIO_JA_EXISTE);
		}
		
		if (emailExiste(usuario.getPerfil().getEmail())) {
			logger.debug("addUsuario() Exceção: "+MensagensErro.EMAIL_JA_EXISTE);
			throw new ProjetoCaronaException(MensagensErro.EMAIL_JA_EXISTE);
		}	
		
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			session.save(usuario);
			transaction.commit();
			session.close();
		}catch(Exception e){
			throw e;
		}
	}
	
	private boolean loginExiste(String login) throws Exception {
		UsuarioDomain usuario;
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(UsuarioDomain.class);
			criteria.add(Restrictions.eq("login", login));
			usuario = (UsuarioDomain) criteria.uniqueResult();
			session.close();
		}catch(Exception e){
			throw e;
		}
		
		if(usuario != null){
			return true;
		}
		return false;
}
	
	private boolean emailExiste(String email) throws Exception {
		PerfilDomain perfil;
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(PerfilDomain.class);
			criteria.add(Restrictions.eq("email", email));
			perfil = (PerfilDomain) criteria.uniqueResult();
			session.close();
		}catch(Exception e){
			throw e;
		}
		
		if(perfil != null){
			return true;
		}
		return false;
	}
	
	@Override
	public void apagaUsuarios(){
		logger.debug("apagando lista de usuarios");
		session = sessionFactory.openSession();	
		transaction = session.beginTransaction();
		session.createQuery("delete from UsuarioDomain").executeUpdate();
		session.createQuery("delete from PerfilDomain").executeUpdate();
		transaction.commit();
		session.close();
	}
}
