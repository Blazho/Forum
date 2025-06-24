import { Component, inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router } from '@angular/router';
import { ThreadService } from '../../../services/thread.service';
import { ThreadDTO } from '../../../api-interfaces/dtos/thread.dto';
import { MatFormFieldModule } from '@angular/material/form-field';
import { TextFieldModule } from '@angular/cdk/text-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { PostDTO } from '../../../api-interfaces/dtos/post.dto';
import { PostService } from '../../../services/post.service';

@Component({
  selector: 'post-creation-body',
  imports: [
    MatCardModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatInputModule,
    TextFieldModule,
    MatProgressSpinnerModule,
    MatButtonModule
  ],
  templateUrl: './post-creation-body.component.html',
  styleUrl: './post-creation-body.component.css'
})
export class PostCreationBodyComponent implements OnInit{

  private readonly activatedRoute = inject(ActivatedRoute)
  private readonly router = inject(Router)
  private readonly threadService = inject(ThreadService)
  private readonly postService = inject(PostService)

  postId: string | null = null
  post: PostDTO | null = null
  thread: ThreadDTO | null = null
  loading = false

  postForm: FormGroup = new FormGroup({
    html: new FormControl('', Validators.required)
  })

  ngOnInit(): void {
    this.postId = this.activatedRoute.snapshot.paramMap.get("postId")
    if(this.postId){
      this.loading = true
      this.postService.getPost(this.postId).subscribe({
        next: result => {
          this.post = result.data
          this.postForm.patchValue(this.post)
          this.loading = false
        },
        error: error => {
          console.log(error);
          this.loading = false
        }
      })
    }

    const threadId = this.activatedRoute.snapshot.paramMap.get('threadId')
    if(threadId){
      this.loading = true
      this.threadService.getThread(+threadId).subscribe({
        next: threadResult => {
          this.thread = threadResult.data
          this.loading = false
        },
        error: error => {
          console.log(error);
          this.loading = false
        }
      })
    }
    
    
  }

  onSubmit() {
    this.loading = true
    const postData: PostDTO = {
      html: this.postForm.value.html,
      threadId: this.thread?.id,
      createdBy: 1, //todo
      lastModifiedBy: 1 //todo
    }
    
    this.postService.createPost(postData, this.postId).subscribe({
      next: result => {
        this.loading = false
        this.router.navigate([`threads/posts/${this.thread?.id}`])
      },
      error: error => {
        console.error(error);
        this.loading = false
      }
    })
    
  }

  cancel() {
    this.router.navigate([`threads/posts/${this.thread?.id}`])
  }

}
