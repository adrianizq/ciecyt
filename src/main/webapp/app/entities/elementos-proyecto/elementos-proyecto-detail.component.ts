import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IElementosProyecto } from 'app/shared/model/elementos-proyecto.model';

@Component({
  selector: 'jhi-elementos-proyecto-detail',
  templateUrl: './elementos-proyecto-detail.component.html'
})
export class ElementosProyectoDetailComponent implements OnInit {
  elementosProyecto: IElementosProyecto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ elementosProyecto }) => {
      this.elementosProyecto = elementosProyecto;
    });
  }

  previousState() {
    window.history.back();
  }
}
