import { Component, inject, OnInit } from '@angular/core';
import { ThreadService } from '../../../services/thread.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatToolbar } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { ThreadDTO } from '../../../api-interfaces/dtos/thread.dto';
import { Pageable } from '../../../api-interfaces/dtos/pageable.dts';

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
    MatToolbar,
    MatCardModule],
  templateUrl: './thread-body.component.html',
  styleUrl: './thread-body.component.css'
})
export class ThreadBodyComponent  implements OnInit{


  private readonly threadService = inject(ThreadService);

  displayedColumns: string[] = ['title', 'status', 'createdBy'];
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

    this.threadService
      .listParentThreads(this.pageableThreads.pageNumber, this.pageableThreads.pageSize)
      .subscribe({
        next: (res) => {
          this.dataSource.data = res.content;
          this.pageableThreads.totalElements = res.totalElements;
          this.pageableThreads.pageNumber = res.pageable.pageNumber;
          this.loading = false;
        },
        error: (erorr: HttpErrorResponse) => {
          console.log(erorr.error);
          this.dataSource.data = [];
          this.loading = false;
        },
      });
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

}
