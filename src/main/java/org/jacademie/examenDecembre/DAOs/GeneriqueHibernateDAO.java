package org.jacademie.examenDecembre.DAOs;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jacademie.examenDecembre.utils.HibernateUtil;

abstract public class GeneriqueHibernateDAO<T extends Object> implements IGeneriqueDAO<T>{

	protected Class<T> clazz;
	protected Logger logger = Logger.getLogger(GeneriqueHibernateDAO.class);
	
	public GeneriqueHibernateDAO(Class<T> clazz) {
		this.clazz = clazz; 
	}

	@Override
	abstract public java.util.List<T> searchByName(String searchPattern);
	abstract public T getOneByName(String searchPattern);
	
	protected Session getSession(){
		return HibernateUtil.getSession();
	}

	protected List<T> searchByPattern(String table, String searchPattern){
		Session hibernateSession = getSession();
		Criteria criteria = hibernateSession.createCriteria(clazz);
    	criteria.add(Restrictions.ilike(table, searchPattern));
    	return criteria.list();
	}
	
	protected T searchOneByPattern(String table, String searchPattern){
		Session hibernateSession = getSession();
		Criteria criteria = hibernateSession.createCriteria(clazz);
    	criteria.add(Restrictions.ilike(table, searchPattern));
    	return (T) criteria.uniqueResult();
	}
	
	@Override
	public T getById(Serializable id) {
		Session hibernateSession = getSession();
		T t = null;
		try{
			t = (T) hibernateSession.get(clazz, id);
			return t;
		}catch(RuntimeException ex){
			logger.error("GenericHibernateDAO<"+clazz.getSimpleName()+">.getById unexpected error.");//,ex
			throw ex;
		}
		
	}


	@Override
	public List<T> getAll() {
		Session hibernateSession = getSession();
		List<T> list = null;
        Query query = hibernateSession.createQuery("from " + clazz.getName());
        list = query.list();
        return list;
	}
	
	
	@Override
	public void save(T entity) {
		Session hibernateSession = getSession();
		hibernateSession.saveOrUpdate(entity);
	}

	@Override
	public void update(T entity) {
		Session hibernateSession = getSession();
		hibernateSession.update(entity);
	}

	@Override
	public void delete(T entity) {
		Session hibernateSession = getSession();
		hibernateSession.delete(entity);
	}
}
