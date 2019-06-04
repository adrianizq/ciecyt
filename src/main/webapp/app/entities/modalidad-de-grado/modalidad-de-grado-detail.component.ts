import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';

@Component({
  selector: 'jhi-modalidad-de-grado-detail',
  templateUrl: './modalidad-de-grado-detail.component.html'
})
export class ModalidadDeGradoDetailComponent implements OnInit {
  modalidadDeGrado: IModalidadDeGrado;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ modalidadDeGrado }) => {
      this.modalidadDeGrado = modalidadDeGrado;
    });
  }

  previousState() {
    window.history.back();
  }
}
