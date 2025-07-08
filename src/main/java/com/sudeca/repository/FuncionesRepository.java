package com.sudeca.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public class FuncionesRepository {

    private final JdbcTemplate jdbcTemplate;

    public FuncionesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LocalDate getFechaCierre(LocalDate fechaUmc, int parametro) {
        String sql = "SELECT contabilidad.lapso_cierre(?, ?)";
        return jdbcTemplate.queryForObject(
                sql,
                LocalDate.class,
                fechaUmc,
                parametro
        );
    }

    public boolean getFechaValor(int idCaho, LocalDate fecha) {
        String sql = "SELECT contabilidad.valida_fecha_valor_comprobante(?, ?)";
        return jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                idCaho,
                fecha
        );
    }

    public boolean getVerificaComprobante(long idComprobante, long idUsuario) {
        String sql = "SELECT contabilidad.verifica_comprobante(?, ?)";
        return jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                idComprobante,
                idUsuario
        );
    }

    public boolean getActualizaComprobante(long idComprobante, long idUsuario) {
        String sql = "SELECT contabilidad.actualiza_comprobante(?, ?)";
        return jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                idComprobante,
                idUsuario
        );
    }
}