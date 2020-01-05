package kharoud.spring.demo.Services;
import kharoud.spring.demo.Model.Customer;
import kharoud.spring.demo.Security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
@Service
@Profile("jpadao")
public class CustomerServiceJPADaoImpl implements CustomerService {

    private EncryptionService encryptionService;
    private EntityManagerFactory emf;

    @PersistenceUnit
    public void setEmf(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public List<Customer> listAllCustomes() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        EntityManager em = emf.createEntityManager();

        return em.find(Customer.class, id);
    }

    @Override
    public Customer saveOrUpdateCustomer(Customer domainObject) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        if (domainObject.getUser() != null && domainObject.getUser().getPassword() != null) {
            domainObject.getUser().setEncryptedPassword(
                    encryptionService.encryptString(domainObject.getUser().getPassword()));
        }

        Customer savedCustomer = em.merge(domainObject);
        em.getTransaction().commit();

        return savedCustomer;
    }

    @Override
    public void deleteCustomer(Integer id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(Customer.class, id));
        em.getTransaction().commit();
    }
}
