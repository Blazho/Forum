import { Component, inject, OnInit } from '@angular/core';
import { PermissionService } from '../../../services/permission.service';
import { PermissionDTO } from '../../../api-interfaces/dtos/permission.dto';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCard } from "@angular/material/card";
import { MatToolbar } from "@angular/material/toolbar";
import { TextFieldModule } from '@angular/cdk/text-field';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'permission-creation-body',
  imports: [MatCard, MatToolbar,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    TextFieldModule,
    MatProgressSpinnerModule,
    MatButtonModule],
  templateUrl: './permission-creation-body.component.html',
  styleUrl: './permission-creation-body.component.css'
})
export class PermissionCreationBodyComponent implements OnInit{

  private readonly permissionService = inject(PermissionService)
  private readonly activateRoute = inject(ActivatedRoute)
  private readonly router = inject(Router)

  isLoading = false
  loadedPermission?: PermissionDTO
  permissionId: string | null = null
  permissionForm: FormGroup = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required)
  })

  ngOnInit(): void {
    this.permissionId = this.activateRoute.snapshot.paramMap.get("permissionId")
    if(this.permissionId){
      this.isLoading = true

      this.permissionService.getPermission(this.permissionId).subscribe({
        next: result => {
          this.loadedPermission = result.data
          this.permissionForm.patchValue(this.loadedPermission)
          this.isLoading = false
        },
        error: error => {
          console.log(error);
          this.isLoading = false
        }
      })
    }
  }

  onSubmit() {
    this.isLoading = true

    const permissionData: PermissionDTO = {
      title: this.permissionForm.value.title,
      description: this.permissionForm.value.description,
    }

    this.permissionService.savePermission(permissionData, this.permissionId)
    .subscribe({
      next: response => {
        this.router.navigate(['permissions'])
        this.isLoading = false
      },
      error: error => {
        console.error(error);
        this.isLoading = false
      }
    })
  }

  cancel() {
    this.router.navigate(['permissions'])
  }
}
