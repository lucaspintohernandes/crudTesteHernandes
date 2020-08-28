package com.hernandes.crudTeste.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hernandes.crudTeste.domain.Usuario;
import com.hernandes.crudTeste.dto.UsuarioDTO;
import com.hernandes.crudTeste.exceptions.ObjectNotFoundException;
import com.hernandes.crudTeste.repositories.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repo;
	
	public Usuario find(Integer id) {
		
		 Optional<Usuario> obj = repo.findById(id);
		 return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName())); 
	
	}
	
	public List<Usuario> findAll() {
		return repo.findAll();
	}
	
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Usuario update(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		repo.deleteById(id);		
	}
	
	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getEmail(), objDto.getSenha());
	}
	
	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setSenha(obj.getSenha());
	}
	
}
