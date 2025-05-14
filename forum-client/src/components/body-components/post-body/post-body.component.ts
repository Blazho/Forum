import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { HandlebarService } from '../../../services/handlebar.service';
import { PostResponse } from '../../../api-interfaces/requests/post.response';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinner } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-post-body',
  imports: [MatPaginatorModule, MatProgressSpinner],
  templateUrl: './post-body.component.html',
  styleUrl: './post-body.component.css'
})
export class PostBodyComponent implements OnInit{

  private readonly handlebarService = inject(HandlebarService);
  pageSize = 10;
  pageIndex = 0;
  isLoading = false;

  handleBarResponse: PostResponse | undefined;

  ngOnInit(): void {
    this.fetchPosts(10);
  }

  handlePageEvent($event: PageEvent) {
    this.pageSize = $event.pageSize;
    this.pageIndex = $event.pageIndex;
    this.fetchPosts(10);
  }

  fetchPosts(threadId: number){
    this.isLoading = true;
    this.handlebarService.getPostsForThread(10, this.pageSize, this.pageIndex).subscribe({
      next: (response) => {
        this.handleBarResponse = response;
        this.isLoading = false;
      },
      error: (error) => {
        console.log(error);
        this.isLoading = false;
      }
    })
  }


}
