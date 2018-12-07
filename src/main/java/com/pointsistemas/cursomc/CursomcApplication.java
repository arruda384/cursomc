package com.pointsistemas.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pointsistemas.cursomc.domain.Categoria;
import com.pointsistemas.cursomc.domain.Cidade;
import com.pointsistemas.cursomc.domain.Cliente;
import com.pointsistemas.cursomc.domain.Endereco;
import com.pointsistemas.cursomc.domain.Estado;
import com.pointsistemas.cursomc.domain.Pagamento;
import com.pointsistemas.cursomc.domain.PagamentoComBoleto;
import com.pointsistemas.cursomc.domain.PagamentoComCartao;
import com.pointsistemas.cursomc.domain.Pedido;
import com.pointsistemas.cursomc.domain.Produto;
import com.pointsistemas.cursomc.domain.enums.EstadoPagamento;
import com.pointsistemas.cursomc.domain.enums.TipoCliente;
import com.pointsistemas.cursomc.repositories.CategoriaRepository;
import com.pointsistemas.cursomc.repositories.CidadeRepository;
import com.pointsistemas.cursomc.repositories.ClienteRepository;
import com.pointsistemas.cursomc.repositories.EnderecoRepository;
import com.pointsistemas.cursomc.repositories.EstadoRepository;
import com.pointsistemas.cursomc.repositories.PagamentoRepository;
import com.pointsistemas.cursomc.repositories.PedidoRepository;
import com.pointsistemas.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
			
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria (null, "Escrítorio");
		
		Produto p1 = new Produto(null,"Computador", 2000.00 );
		Produto p2 = new Produto(null,"Impresora", 800.00 );
		Produto p3 = new Produto(null,"Mouse", 80.00 );
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));		
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);

		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campina", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));		
		
	
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3)); 
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "07280468497", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("97080880", "98887788"));
			
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Av Matoss", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2018 10:20"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2018 19:30"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2018 00:00"), null);
		ped2.setPagamento(pagto2);
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
	}
	
	
}
