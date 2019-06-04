import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacultad } from 'app/shared/model/facultad.model';

@Component({
  selector: 'jhi-facultad-detail',
  templateUrl: './facultad-detail.component.html'
})
export class FacultadDetailComponent implements OnInit {
  facultad: IFacultad;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ facultad }) => {
      this.facultad = facultad;
    });
  }

  previousState() {
    window.history.back();
  }
}
