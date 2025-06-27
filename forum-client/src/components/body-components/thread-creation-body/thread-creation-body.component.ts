import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { ThreadDTO } from '../../../api-interfaces/dtos/thread.dto';
import { ActivatedRoute, Router } from '@angular/router';
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
  loadedThread?: ThreadDTO
  threadId: string | null = null

  private readonly router = inject(Router)
  private readonly activateRoute = inject(ActivatedRoute)
  private readonly threadService = inject(ThreadService)



  ngOnInit(): void {
    this.threadService.listParentableThreads().subscribe({
      next: result => {
        this.parentThreads = result.data
      },
      error: error => {
        console.log(error);
      }
    })

    this.threadId = this.activateRoute.snapshot.paramMap.get('threadId')
    if (this.threadId) {
      this.loading = true
      this.threadService.getThread(+this.threadId).subscribe(
        {
          next: threadResult => {
            this.loadedThread = threadResult.data
            this.threadForm.patchValue(this.loadedThread)
            if (this.loadedThread.hasChildren) {
              this.threadForm.get('parentThreadId')?.disable()
            } else {
              this.threadForm.get('parentThreadId')?.enable()
            }
            
            this.loading = false
          },
          error: error => {
            console.error(error.error.errorMessage);
            this.loading = false
          }
        }
      )
    }

  }



  onSubmit() {
    this.loading = true
    const threadData: ThreadDTO = {
      title: this.threadForm.value.title,
      description: this.threadForm.value.description,
      status: this.threadForm.value.status,
      parentThreadId: this.threadForm.value.parentThreadId
    }

    this.threadService.createThread(threadData, this.threadId).subscribe({
      next: result => {
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
