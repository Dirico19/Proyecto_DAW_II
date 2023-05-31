package com.cibertec.springboot.web.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cibertec.springboot.web.app.models.service.UsuarioDetallesServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UsuarioDetallesServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/css/**","/img/**","/js/**","/layout/**").permitAll()
		.antMatchers("/socio/registroDatos","/socio/registroUsuario").permitAll()
		.antMatchers("/libro/registro","/libro/editar/**","/libro/eliminar/**").hasAnyAuthority("Administrador","Mantenimiento")
		.antMatchers("/prestamo/solicitar/**","/prestamo/confirmar","/prestamo/devolver/**","/prestamo/devolver").hasAnyAuthority("Socio")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		.successForwardUrl("/login_success")
		.failureForwardUrl("/login_error")
		.and()
		.logout().permitAll()
		;
		
		return http.build();
	}
	
}
