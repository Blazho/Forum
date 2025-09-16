import { Component, inject, OnInit } from '@angular/core';
import { ThreadService } from '../../../services/thread.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { ThreadDTO } from '../../../api-interfaces/dtos/thread.dto';
import { Pageable } from '../../../api-interfaces/dtos/pageable.dts';
import { ActivatedRoute, Params, Router, RouterLink } from '@angular/router';
import { MatTooltipModule } from '@angular/material/tooltip';
import { AuthService } from '../../../services/auth.service';
import { PermissionLayer, PermissionName } from '../../../api-interfaces/responses/login.response';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../../reusables/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-thread-body',
  imports: [MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatProgressBarModule,
    MatCardModule,
    MatToolbarModule,
    MatTooltipModule,
    RouterLink
  ],
  templateUrl: './thread-body.component.html',
  styleUrl: './thread-body.component.css'
})
export class ThreadBodyComponent  implements OnInit{

  private readonly router = inject(Router)
  private readonly activatedRoute = inject(ActivatedRoute)
  private readonly threadService = inject(ThreadService);
  private readonly authService = inject(AuthService);
  private readonly dialog = inject(MatDialog)

  displayedColumns: string[] = ['title', 'description', 'status', 'createdBy', 'actions'];
  dataSource = new MatTableDataSource<ThreadDTO>();

  searchTerm = '';
  selectedStatus = '';
  loading = false;
  pageableThreads: Pageable = {
    pageNumber: 0,
    pageSize: 10,
    totalElements: 0
  };


  ngOnInit(): void {
    this.loadThreads()
  }

  
  loadThreads(): void {
    this.loading = true;

    const threadId = this.activatedRoute.snapshot.paramMap.get('threadId')
    const threads$ = threadId
      ? this.threadService.findChildThreads(+threadId, this.pageableThreads.pageNumber, this.pageableThreads.pageSize)
      : this.threadService.listParentThreads(this.pageableThreads.pageNumber, this.pageableThreads.pageSize)

    threads$.subscribe({
      next: (res) => {
        this.dataSource.data = res.data.content
        this.pageableThreads.totalElements = res.data.totalElements;
        this.pageableThreads.pageNumber = res.data.pageable.pageNumber;
        this.loading = false;
      },
      error: (erorr: HttpErrorResponse) => {
        console.log(erorr.error);
        this.dataSource.data = [];
        this.loading = false;
      }
    });
    
  }

  goToThread(threadId: number) {
    let layer = this.activatedRoute.snapshot.queryParamMap.get('layer')
    if (layer == '2') {
      this.router.navigate(['/threads/posts/', threadId])
    } else {
      const queryParam: Params = { layer: '2' }
      this.router.navigate(['/threads', threadId],
        {
          relativeTo: this.activatedRoute,
          queryParams: queryParam,
          queryParamsHandling: 'merge'
        },
      )
    }
  }

  applyFilters() {
    this.pageableThreads.pageNumber = 0
    this.loadThreads()
  }

  changePage(event: PageEvent): void {
    this.pageableThreads.pageNumber = event.pageIndex;
    this.pageableThreads.pageSize = event.pageSize;
    this.loadThreads()
  }

  isLoggedIn(){
    return this.authService.getAuthToken() != null
  }

  private canViewParentThreads(): boolean{
    return this.authService.hasPermission(PermissionName.THREAD_PARENT_PERMISSION, PermissionLayer.VIEW)
  }

  private canViewChildThreads(): boolean{
    return this.authService.hasPermission(PermissionName.THREAD_CHILD_PERMISSION, PermissionLayer.VIEW)
  }
  //possibly redundant permission check because of route guard
  canViewThreads(){
    const layer = this.activatedRoute.snapshot.queryParamMap.get('layer')
    if(layer == '2'){
      return this.canViewChildThreads()
    } else {
      return this.canViewParentThreads()
    }
  }

  canCreateThreads(): boolean{
    return this.authService.hasPermission(PermissionName.THREAD_PARENT_PERMISSION, PermissionLayer.CREATE) ||
           this.authService.hasPermission(PermissionName.THREAD_CHILD_PERMISSION, PermissionLayer.CREATE)
  }

  canEditThreads(authorId?: number): boolean{
    const layer = this.activatedRoute.snapshot.queryParamMap.get('layer')
    if(layer == '2'){
      return this.canEditChildThreads(authorId)
    } else {
      return this.canEditParentThreads(authorId)
    }
  } 

  private canEditChildThreads(authorId: number | undefined): boolean {
    const logedInUserId = this.authService.getUserId()
    return logedInUserId == authorId && this.authService.hasPermission(PermissionName.THREAD_CHILD_PERMISSION, PermissionLayer.EDIT)
  }

  private canEditParentThreads(authorId: number | undefined): boolean {
    const logedInUserId = this.authService.getUserId()
    return logedInUserId == authorId && this.authService.hasPermission(PermissionName.THREAD_PARENT_PERMISSION, PermissionLayer.EDIT)
  }

  canDeleteThreads(): boolean {
    const layer = this.activatedRoute.snapshot.queryParamMap.get('layer')
    if(layer == '2'){
      return this.authService.hasPermission(PermissionName.THREAD_CHILD_PERMISSION, PermissionLayer.DELETE)
    } else {
      return this.authService.hasPermission(PermissionName.THREAD_PARENT_PERMISSION, PermissionLayer.DELETE)
    }
  }

  confirmDelete($event: MouseEvent, threadId: number) {
    $event.stopPropagation();
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      data: {
        dialogContent: "Do you want to delete this thread?"
      }
    }).afterClosed().subscribe({
      next: result => {
        if(result == "Yes"){
          this.threadService.deleteThread(threadId).subscribe({
            next: (response) => {
              console.log(response);
              this.loadThreads()
            },
            error: (error) => {
              console.log(error);
            }
          })
        }
      }
    })
  }

}
