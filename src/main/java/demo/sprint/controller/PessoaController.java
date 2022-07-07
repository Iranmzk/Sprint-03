package demo.sprint.controller;


import demo.sprint.model.Pessoa;
import demo.sprint.model.mapper.PessoaMapper;
import demo.sprint.model.request.PessoaRequest;
import demo.sprint.model.response.PessoaResponse;
import demo.sprint.model.response.PessoaResponseSenha;
import demo.sprint.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/sprint03")
public class PessoaController {
    private  PessoaService service;

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponseSenha save(@RequestBody @Valid PessoaRequest pessoaRequest){
        return PessoaMapper.pessoaResponseSenha(service.save(PessoaMapper.requestPessoa(pessoaRequest)));
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponseSenha updatePessoa (@PathVariable @Valid String id,@RequestBody PessoaRequest pessoaRequest){
        return PessoaMapper.pessoaResponseSenha(service.att(id, PessoaMapper.requestPessoa(pessoaRequest)));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestParam("id") List<String> id){
        service.deleteAllById(id);
    }
    @GetMapping("/pessoa/{id}")
    public Pessoa findById(@PathVariable String id){
        return service.findById(id);
    }

    @GetMapping("/admin")
    public List<PessoaResponseSenha> findAll(){
        return service.findAll()
                .stream()
                .map(PessoaMapper::pessoaResponseSenha)
                .toList();
    }

    @GetMapping()
    public List<PessoaResponse> findAllPessoa(){
        return service.findAll()
                .stream()
                .map(PessoaMapper::pessoaResponse)
                .toList();
    }

    @GetMapping("/create-coockie")
    public String criandoCoookie(Pessoa pessoa, HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("cookieTest","cookie-value");
        cookie.getName();
        cookie.getComment();
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        return "cookie-recived/";
    }

    @GetMapping("/filtro")
    public List<PessoaResponse> findPessoaNomeContains(@RequestParam String nome){
        return service.findByNomeContains(nome)
                .stream()
                .map(PessoaMapper::pessoaResponse)
                .toList();
    }
}

