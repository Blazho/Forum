import { Component, inject, OnInit } from '@angular/core';
import { HandlebarService } from '../../../services/handlebar.service';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { PostDTO } from '../../../api-interfaces/dtos/post.dto';
import { Pageable } from '../../../api-interfaces/dtos/pageable.dts';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { ThreadDTO } from '../../../api-interfaces/dtos/thread.dto';
import { ThreadService } from '../../../services/thread.service';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../../services/auth.service';
import { MatTooltipModule } from '@angular/material/tooltip';
import { PermissionLayer, PermissionName } from '../../../api-interfaces/responses/login.response';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../reusables/confirmation-dialog/confirmation-dialog.component';
import { PostService } from '../../../services/post.service';

@Component({
  selector: 'app-post-body',
  imports: [MatPaginatorModule,
    MatProgressSpinner,
    RouterLink,
    MatIconModule,
    MatToolbarModule,
    MatTooltipModule,
    MatCardModule,
    MatMenuModule,
    MatButtonModule,
  ],
  templateUrl: './post-body.component.html',
  styleUrl: './post-body.component.css'
})
export class PostBodyComponent implements OnInit{

  private readonly handlebarService = inject(HandlebarService);
  private readonly activatedRoute = inject(ActivatedRoute)
  private readonly threadService = inject(ThreadService)
  private readonly authService = inject(AuthService)
  private readonly dialog = inject(MatDialog)
  private readonly postService = inject(PostService)
  
  isLoading = false;
  thread?: ThreadDTO

  postContent: PostDTO[] | undefined;
  pageablePosts: Pageable = {
    pageNumber: 0,
    pageSize: 10,
    totalElements: 0
  };

  ngOnInit(): void {
    const threadId = this.activatedRoute.snapshot.paramMap.get('threadId')
    //find another way to send thread from parent component
    if(threadId){
      this.threadService.getThread(+threadId).subscribe({
        next: threadResponse => {
          this.thread = threadResponse.data
          this.fetchPosts()
        },
        error: (error) => {
        console.log(error);
        this.isLoading = false;
      }
      })
    }


  }

  handlePageEvent($event: PageEvent) {
    this.pageablePosts.pageNumber = $event.pageIndex
    this.pageablePosts.pageSize = $event.pageSize
    this.fetchPosts();
  }

  fetchPosts() {
    if (this.thread) {
      this.isLoading = true;
      this.handlebarService.getPosts(this.thread.id!!, this.pageablePosts.pageSize, this.pageablePosts.pageNumber).subscribe({
        next: (response) => {
          this.postContent = response.content
          this.pageablePosts.pageNumber = response.pageable.pageNumber
          this.pageablePosts.pageSize = response.pageable.pageSize
          this.pageablePosts.totalElements = response.totalElements

          this.isLoading = false;
        },
        error: (error) => {
          console.log(error);
          this.isLoading = false;
        }
      })
    }
  }

  openDeleteDialog(postId: number|undefined) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        dialogContent: "Do you want to delete this post?"
      }
    }).afterClosed().subscribe({
      next: result => {
        if(result == "Yes"){
          this.postService.deletePost(postId!!).subscribe({
            next: (response) => {
              console.log(response);
              this.fetchPosts()
            },
            error: (error) => {
              console.log(error);
            }
          })
        }
      }
    })
  }

  canViewPosts(): boolean{
    return this.authService.hasPermission(PermissionName.POST_PERMISSION, PermissionLayer.VIEW)
  }

  canCreatePosts(): boolean{
    return this.authService.hasPermission(PermissionName.POST_PERMISSION, PermissionLayer.CREATE)
  }

  canEditPosts(authorId?: number): boolean{
    return this.authService.getUserId() == authorId && this.authService.hasPermission(PermissionName.POST_PERMISSION, PermissionLayer.EDIT)
  }

  canDeletePost(): boolean {
    return this.authService.hasPermission(PermissionName.POST_PERMISSION, PermissionLayer.DELETE)
  }
}
