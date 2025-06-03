import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { ThreadDTO } from '../../../api-interfaces/dtos/thread.dto';
import { Router } from '@angular/router';
import { ThreadService } from '../../../services/thread.service';
import { ThreadPairProjection } from '../../../api-interfaces/projections/thread-pair.projection';

@Component({
  selector: 'app-thread-creation-body',
  imports: [MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    ReactiveFormsModule,
    MatCardModule],
  templateUrl: './thread-creation-body.component.html',
  styleUrl: './thread-creation-body.component.css'
})
export class ThreadCreationBodyComponent implements OnInit{
  threadForm: FormGroup = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    status: new FormControl(''),
    parentThreadId: new FormControl('')
  });
  parentThreads: ThreadPairProjection[] = []
  loading = false

  private readonly router = inject(Router)
  private readonly threadService = inject(ThreadService)



  ngOnInit(): void {
    this.loading = true
    this.threadService.listParentableThreads().subscribe({
      next: result => {
        this.parentThreads = result.data
        this.loading = false
      },
      error: error => {
        console.log(error);
        
        this.loading = false
      }
    })
    
  }



  onSubmit() {
    this.loading = true
    const threadData: ThreadDTO = {
      title: this.threadForm.value.title,
      description: this.threadForm.value.description,
      status: this.threadForm.value.status,
      createdBy: 1, //todo load id from logged in user
      parentThreadId: this.threadForm.value.parentThreadId
    }

    this.threadService.createThread(threadData).subscribe({
      next: result => {
        console.log(result);
        this.loading = false
        this.router.navigate(['threads'])
      },
      error: error => {
        
        console.error(error.error.errorMessage);
        this.loading = false
      }
    })
    
  }

}
