package edu.alumno.videogames.filters.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import edu.alumno.videogames.filters.model.FiltroBusqueda;
import edu.alumno.videogames.filters.specification.operacion.ContieneOperacionStrategy;
import edu.alumno.videogames.filters.specification.operacion.IgualOperacionStrategy;
import edu.alumno.videogames.filters.specification.operacion.MayorQueOperacionStrategy;
import edu.alumno.videogames.filters.specification.operacion.MenorQueOperacionStrategy;
import edu.alumno.videogames.filters.specification.operacion.OperacionBusquedaStrategy;

public class FiltroBusquedaSpecification<T> implements Specification<T> {

    private List<FiltroBusqueda> filtrosBusqueda;
    private final List<OperacionBusquedaStrategy> estrategias;

    private List<OperacionBusquedaStrategy> getDefaultStrategies() {
        return List.of(
                new IgualOperacionStrategy(),
                new ContieneOperacionStrategy(),
                new MayorQueOperacionStrategy(),
                new MenorQueOperacionStrategy());
    }

    public FiltroBusquedaSpecification(List<FiltroBusqueda> filtrosBusqueda) {
        this.filtrosBusqueda = filtrosBusqueda;
        this.estrategias = getDefaultStrategies();
    }

    public FiltroBusquedaSpecification(List<FiltroBusqueda> filtrosBusqueda,
            List<OperacionBusquedaStrategy> estrategias) {
        this.filtrosBusqueda = filtrosBusqueda;
        this.estrategias = estrategias;
    }

    // Método Builder
    public Specification<T> build(List<FiltroBusqueda> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicados = filtros.stream()
                    .map(filtro -> crearPredicado(root, criteriaBuilder, filtro))
                    .collect(Collectors.toList());

            return predicados.isEmpty()
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.and(predicados.toArray(new Predicate[0]));
        };
    }

    private Predicate crearPredicado(Root<T> root, CriteriaBuilder criteriaBuilder, FiltroBusqueda filtro) {
        return estrategias.stream()
                .filter(estrategia -> estrategia.soportaOperacion(filtro.getOperacion()))
                .findFirst()
                .map(estrategia -> estrategia.crearPredicado(root, criteriaBuilder, filtro))
                .orElseThrow(() -> new UnsupportedOperationException(
                        "Operador de filtro no permitido: " + filtro.getOperacion()));
    }

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        if (filtrosBusqueda == null || filtrosBusqueda.isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        List<Predicate> predicados = filtrosBusqueda.stream()
                .map(filtro -> crearPredicado(root, criteriaBuilder, filtro))
                .collect(Collectors.toList());
        return predicados.isEmpty()
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.and(predicados.toArray(new Predicate[0]));
    }
}
