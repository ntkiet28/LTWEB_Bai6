package Configs.Database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;


//class này cấu hình chung cho JPA
@PersistenceContext
public class JPAConfig {

	public static EntityManager getEntityManager() {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dataSource");

		return factory.createEntityManager();

	}

}
