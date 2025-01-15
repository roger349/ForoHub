package com.rer.ForoHub.Models.Model;

import org.springframework.data.domain.Page;

import java.util.List;

public class TopicoRespuestas {

        private List<Respuestas> respuestas;
        private int totalPaginas;
        private long totalElementos;
        private int numeroPagina;
        private int tamañoPagina;

        public TopicoRespuestas(Page<Respuestas> respuestasPage) {

            this.respuestas = respuestasPage.getContent();
            this.totalPaginas = respuestasPage.getTotalPages();
            this.totalElementos = respuestasPage.getTotalElements();
            this.numeroPagina = respuestasPage.getNumber();
            this.tamañoPagina = respuestasPage.getSize();
        }
        public List<Respuestas> getRespuestas() {return respuestas;}
        public void setRespuestas(List<Respuestas> respuestas) {this.respuestas = respuestas;}
        public int getTotalPaginas() {return totalPaginas;}
        public void setTotalPaginas(int totalPaginas) {this.totalPaginas = totalPaginas;}
        public long getTotalElementos() {return totalElementos;}
        public void setTotalElementos(long totalElementos) {this.totalElementos = totalElementos;}
        public int getNumeroPagina() {return numeroPagina;}
        public void setNumeroPagina(int numeroPagina) {this.numeroPagina = numeroPagina;}
        public int getTamañoPagina() {return tamañoPagina;}
        public void setTamañoPagina(int tamañoPagina) {this.tamañoPagina = tamañoPagina;}
}
