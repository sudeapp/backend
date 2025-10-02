package com.sudeca.repository;

import com.sudeca.dto.LibroDiarioDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    public boolean getMesCierre(int idCaho, LocalDate fecha) {
        String sql = "SELECT contabilidad.mes_cierre(?, ?)";
        return jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                idCaho,
                fecha
        );
    }

    public String callValidaCodigoContable(Long idCaho, String pCuenta) {
        String sql = "SELECT contabilidad.valida_codigo_contable(?, ?)";

        return jdbcTemplate.queryForObject(
                sql,
                String.class,
                idCaho,
                pCuenta
        );
    }

    public String callValidaCodigoContablePlantilla(Long idCaho, String pCuenta) {
        String sql = "SELECT contabilidad.valida_codigo_contable_plantilla(?, ?)";

        return jdbcTemplate.queryForObject(
                sql,
                String.class,
                idCaho,
                pCuenta
        );
    }

    public boolean getValidaNivelPlantilla(Long idPPlan) {
        String sql = "SELECT contabilidad.valida_niveles_plan_contable(?)";
        return jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                idPPlan
        );
    }

    public boolean getCierreMensual(Long idCaho,Long idUsuario) {
        String sql = "SELECT contabilidad.cierre_mensual(?,?)";
        return jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                idCaho,
                idUsuario
        );
    }

    public boolean getCierreAnual(Long idCaho,Long idUsuario) {
        String sql = "SELECT contabilidad.asiento_cierre_ejercicio(?,?)";
        return jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                idCaho,
                idUsuario
        );
    }
}