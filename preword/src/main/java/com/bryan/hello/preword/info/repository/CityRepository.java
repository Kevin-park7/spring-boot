package com.bryan.hello.preword.info.repository;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bryan.hello.preword.info.model.City;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CityRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final CityRowMapper cityRowMapper;
	
	public CityRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.cityRowMapper = new CityRowMapper();
	}

	public List<City> findList() {
		
		log.debug("findList query = {}",CitySql.SELECT);
		return namedParameterJdbcTemplate.query(CitySql.SELECT,
						EmptySqlParameterSource.INSTANCE,
						this.cityRowMapper);
		
//		String sql = "select * from city limit 1000";
//		log.debug("query : {}", sql);
//		RowMapper<City> cityMapper = (rs, rowNum) -> {
//			City city = new City();
//			city.setId(rs.getInt("ID"));
//			city.setName(rs.getString("Name"));
//			city.setCountryCode(rs.getString("countrycode"));
//			city.setDistrict(rs.getString("district"));
//			city.setPopulation(rs.getInt("population"));
//			return city;
//		};
//		return namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(), cityMapper);
	}
	public List<City> findByCountryCodeAndPopulation(String countryCode, int population) {
		
		String qry = CitySql.SELECT
				+ CitySql.COUNTRY_CODE_CONDITION
				+ CitySql.POPULATION_CONDITION;

		SqlParameterSource param = new MapSqlParameterSource("countryCode", countryCode)
				.addValue("population", population);
		
		return namedParameterJdbcTemplate.query(qry, param, this.cityRowMapper);
	}

	
}
