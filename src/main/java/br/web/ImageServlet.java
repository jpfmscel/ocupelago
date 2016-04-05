package br.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.dao.ImagemDAO;
import br.entidades.Imagem;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ImagemDAO imagemDAO = new ImagemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getPathInfo().substring(1);
        Imagem image = imagemDAO.buscarPorId(Integer.parseInt(id));
        response.setContentType(getServletContext().getMimeType(image.getNomeArquivo()));
        response.setContentLength(image.getData().length);
        response.getOutputStream().write(image.getData());
    }

}