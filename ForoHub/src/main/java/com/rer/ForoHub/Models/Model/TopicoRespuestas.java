package com.rer.ForoHub.Models.Model;

import org.springframework.data.domain.Page;

import java.util.List;

public class TopicoRespuestas {

        private List<Respuestas> respuestas;
        private int totalPages;
        private long totalElements;
        private int pageNumber;
        private int pageSize;

        public TopicoRespuestas(Page<Respuestas> respuestasPage) {

            this.respuestas = respuestasPage.getContent();
            this.totalPages = respuestasPage.getTotalPages();
            this.totalElements = respuestasPage.getTotalElements();
            this.pageNumber = respuestasPage.getNumber();
            this.pageSize = respuestasPage.getSize();
        }
        public List<Respuestas> getRespuestas() {return respuestas;}
        public void setRespuestas(List<Respuestas> respuestas) {this.respuestas = respuestas;}
        public int getTotalPages() {return totalPages;}
        public void setTotalPages(int totalPages) {this.totalPages = totalPages;}
        public long getTotalElements() {return totalElements;}
        public void setTotalElements(long totalElements) {this.totalElements = totalElements;}
        public int getPageNumber() {return pageNumber;}
        public void setPageNumber(int pageNumber) {this.pageNumber = pageNumber;}
        public int getPageSize() {return pageSize;}
        public void setPageSize(int pageSize) {this.pageSize = pageSize;}
}
