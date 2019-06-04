import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';

@Component({
  selector: 'jhi-roles-usuario-proyecto-detail',
  templateUrl: './roles-usuario-proyecto-detail.component.html'
})
export class RolesUsuarioProyectoDetailComponent implements OnInit {
  rolesUsuarioProyecto: IRolesUsuarioProyecto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ rolesUsuarioProyecto }) => {
      this.rolesUsuarioProyecto = rolesUsuarioProyecto;
    });
  }

  previousState() {
    window.history.back();
  }
}
