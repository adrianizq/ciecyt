import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpresas } from 'app/shared/model/empresas.model';

@Component({
  selector: 'jhi-empresas-detail',
  templateUrl: './empresas-detail.component.html'
})
export class EmpresasDetailComponent implements OnInit {
  empresas: IEmpresas;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ empresas }) => {
      this.empresas = empresas;
    });
  }

  previousState() {
    window.history.back();
  }
}
