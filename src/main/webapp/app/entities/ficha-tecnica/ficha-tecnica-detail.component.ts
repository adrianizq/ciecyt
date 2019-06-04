import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFichaTecnica } from 'app/shared/model/ficha-tecnica.model';

@Component({
  selector: 'jhi-ficha-tecnica-detail',
  templateUrl: './ficha-tecnica-detail.component.html'
})
export class FichaTecnicaDetailComponent implements OnInit {
  fichaTecnica: IFichaTecnica;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fichaTecnica }) => {
      this.fichaTecnica = fichaTecnica;
    });
  }

  previousState() {
    window.history.back();
  }
}
