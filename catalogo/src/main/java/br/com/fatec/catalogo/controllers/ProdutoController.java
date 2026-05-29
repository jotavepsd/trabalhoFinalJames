package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.services.CategoriaService;
import br.com.fatec.catalogo.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(@RequestParam(value = "nome", required = false) String nome,
                         @RequestParam(value = "categoriaId", required = false) Long categoriaId,
                         Model model) {

        if (nome != null && !nome.isBlank()) {
            model.addAttribute("produtos", service.listarPorNome(nome));
        } else if (categoriaId != null) {
            model.addAttribute("produtos", service.listarPorCategoria(categoriaId));
        } else {
            model.addAttribute("produtos", service.listarTodos());
        }

        model.addAttribute("categorias", categoriaService.listarTodas());
        return "lista-produtos";
    }

    @GetMapping("/admin/consulta")
    public String consultaAdmin(Model model) {
        model.addAttribute("produtos", service.listarParaAdmin());
        return "consulta-admin";
    }

    @GetMapping("/novo")
    public String exibirFormulario(Model model) {
        model.addAttribute("produto", new ProdutoModel());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "cadastro-produto";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("produto") ProdutoModel produto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodas());
            return "cadastro-produto";
        }

        try {
            // Verifica se é uma edição ou um novo cadastro antes de salvar
            boolean isEdicao = produto.getIdProduto() > 0;

            service.salvar(produto);

            String horarioFormatado = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            String operacao = isEdicao ? "alterado" : "cadastrado";

            redirectAttributes.addFlashAttribute("msgSucesso",
                    "Produto '" + produto.getNome() + "' " + operacao + " com sucesso às " + horarioFormatado + ".");

        } catch (RuntimeException e) {
            model.addAttribute("msgErro", e.getMessage());
            model.addAttribute("categorias", categoriaService.listarTodas());
            return "cadastro-produto";
        }

        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable long id, Model model) {
        model.addAttribute("produto", service.buscarPorId(id));
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "cadastro-produto";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable long id, RedirectAttributes redirectAttributes) {
        service.excluir(id);
        String horarioFormatado = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        redirectAttributes.addFlashAttribute("msgSucesso", "Produto removido com sucesso às " + horarioFormatado + ".");
        return "redirect:/produtos";
    }
}