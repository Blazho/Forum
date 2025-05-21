import { Component, inject, OnInit } from '@angular/core';
import { HandlebarService } from '../../../services/handlebar.service';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { Post } from '../../../api-interfaces/dtos/post.dto';
import { Pageable } from '../../../api-interfaces/dtos/pageable.dts';

@Component({
  selector: 'app-post-body',
  imports: [MatPaginatorModule, MatProgressSpinner],
  templateUrl: './post-body.component.html',
  styleUrl: './post-body.component.css'
})
export class PostBodyComponent implements OnInit{

  private readonly handlebarService = inject(HandlebarService);
  isLoading = false;

  postContent: Post[] | undefined;
  pageablePosts: Pageable = {
    pageNumber: 0,
    pageSize: 10,
    totalPosts: 0
  };

  ngOnInit(): void {
    this.fetchPosts(10);
  }

  handlePageEvent($event: PageEvent) {
    this.pageablePosts.pageNumber = $event.pageIndex
    this.pageablePosts.pageSize = $event.pageSize
    this.fetchPosts(10);
  }

  fetchPosts(threadId: number){
    this.isLoading = true;
    this.handlebarService.getPosts(10, this.pageablePosts.pageSize, this.pageablePosts.pageNumber).subscribe({
      next: (response) => {
        this.postContent = response.content
        this.pageablePosts.pageNumber = response.pageable.pageNumber
        this.pageablePosts.pageSize = response.pageable.pageSize
        this.pageablePosts.totalPosts = response.totalElements

        this.isLoading = false;
      },
      error: (error) => {
        console.log(error);
        this.isLoading = false;
      }
    })
  }


}
