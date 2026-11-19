package br.com.agenda_facil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

import br.com.agenda_facil.model.Contato;
import br.com.agenda_facil.repository.ContatoRepository;

// @Controller informa ao Spring que esta classe controla
// as requisições e páginas da aplicação.
@Controller
public class ContatoController {

    // Referência ao repositório responsável pelo acesso ao banco.
    private final ContatoRepository contatoRepository;

    // Construtor: o Spring fornece automaticamente o repositório.
    // Esse mecanismo é chamado de injeção de dependência.
    public ContatoController(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    // @GetMapping responde às requisições GET.
    // Exibe a página com a lista de contatos.
    @GetMapping("/contatos")
    public String listar(Model model) {

        // Busca todos os contatos cadastrados no banco.
        model.addAttribute("contatos", contatoRepository.findAll());

        // Envia também a quantidade de contatos para a página.
        model.addAttribute("total", contatoRepository.count());

        // Abre o arquivo contatos.html.
        return "contatos";
    }

    // Abre o formulário para cadastrar um novo contato.
    @GetMapping("/contatos/novo")
    public String novo(Model model) {

        // Envia um objeto vazio para preencher o formulário.
        model.addAttribute("contato", new Contato());

        // Informa que estamos cadastrando um novo contato.
        model.addAttribute("titulo", "Novo contato");

        return "formulario";
    }

    // @PostMapping recebe os dados enviados pelo formulário.
    @PostMapping("/contatos/salvar")
    public String salvar(
            @Valid @ModelAttribute("contato") Contato contato,
            BindingResult resultado,
            Model model) {

        // Verifica se os dados enviados passaram pelas validações.
        if (resultado.hasErrors()) {

            model.addAttribute("titulo",
                    contato.getId() == null ? "Novo contato" : "Editar contato");

            // Se houver erro, retorna ao formulário sem salvar.
            return "formulario";
        }

        // Se o ID estiver vazio, cadastra.
        // Se o ID existir, atualiza o registro.
        contatoRepository.save(contato);

        // Retorna para a lista de contatos.
        return "redirect:/contatos";
    }

    // Abre o formulário preenchido com os dados do contato.
    @GetMapping("/contatos/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        // Busca o contato pelo ID.
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Contato não encontrado: " + id));

        model.addAttribute("contato", contato);
        model.addAttribute("titulo", "Editar contato");

        return "formulario";
    }

    // @PostMapping garante que a exclusão será feita por POST.
    // Não usamos GET para excluir registros.
    @PostMapping("/contatos/excluir/{id}")
    public String excluir(@PathVariable Long id) {

        // Exclui o contato pelo identificador.
        contatoRepository.deleteById(id);

        return "redirect:/contatos";
    }
}
