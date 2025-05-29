import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { PageResponse } from "../api-interfaces/responses/page.response";
import { ThreadDTO } from "../api-interfaces/dtos/thread.dto";
import { Observable } from "rxjs";
import { ApiResponse } from "../api-interfaces/responses/api.response";
import { T } from "@angular/cdk/keycodes";

@Injectable({
        providedIn: 'root'
})
export class ThreadService {
    private readonly http = inject(HttpClient)
    private readonly url = "/api/thread"

    listParentThreads(pageNum: number, pageSize: number): Observable<PageResponse<ThreadDTO>> {
        const params = new HttpParams()
        .set("pageNum", pageNum)
        .set("pageSize", pageSize)
        return this.http.get<PageResponse<ThreadDTO>>(`${this.url}/list`, { params })
    }

    getThread(threadId: number): Observable<ApiResponse<ThreadDTO>> {
        return this.http.get<ApiResponse<ThreadDTO>>(`${this.url}/${threadId}`)
    }

    findChildThreads(threadId: number, pageNum: number, pageSize: number): Observable<ApiResponse<PageResponse<ThreadDTO>>> {
        const params = new HttpParams()
        .set("pageNum", pageNum)
        .set("pageSize", pageSize)
        return this.http.get<ApiResponse<PageResponse<ThreadDTO>>>(`${this.url}/list/${threadId}`, { params })
    }

}