package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.domain.Provider;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ProviderJpaRepository extends BaseJpaRepository<Provider> implements ProviderRepository {

	@Override
	public Optional<Provider> findByNif(String nif) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Provider.findByNif", Provider.class).setParameter(1, nif).getResultStream().findFirst();
	}

	@Override
	public List<Provider> findByNameMailPhone(String name, String email, String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Provider> findByName(String name) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Provider.findByName", Provider.class).setParameter(1, name).getResultStream()
				.collect(Collectors.toList());
	}

	@Override
	public List<Provider> findBySparePartCode(String code) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Provider.findBySparePartCode", Provider.class).setParameter(1, code).getResultStream()
				.collect(Collectors.toList());
	}

}
