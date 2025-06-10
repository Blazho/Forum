import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { PageResponse } from "../api-interfaces/responses/page.response";
import { ThreadDTO } from "../api-interfaces/dtos/thread.dto";
import { Observable } from "rxjs";
import { ApiResponse } from "../api-interfaces/responses/api.response";
import { ThreadPairProjection } from "../api-interfaces/projections/thread-pair.projection";

@Injectable({
        providedIn: 'root'
})
export class ThreadService {
    private readonly http = inject(HttpClient)
    private readonly url = "/api/thread"

    listParentThreads(pageNum: number, pageSize: number): Observable<ApiResponse<PageResponse<ThreadDTO>>> {
        const params = new HttpParams()
        .set("pageNum", pageNum)
        .set("pageSize", pageSize)
        return this.http.get<ApiResponse<PageResponse<ThreadDTO>>>(`${this.url}/list`, { params })
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

    listParentableThreads(): Observable<ApiResponse<ThreadPairProjection[]>> {
        return this.http.get<ApiResponse<ThreadPairProjection[]>>(`${this.url}/parentable`)
    }

    createThread(threadDTO: ThreadDTO, threadId?: string | null): Observable<ApiResponse<ThreadDTO>> {
        if (threadId) {
            return this.http.put<ApiResponse<ThreadDTO>>(`${this.url}/edit/${threadId}`, threadDTO)
        } else {
            return this.http.post<ApiResponse<ThreadDTO>>(`${this.url}/create`, threadDTO)
        }
        
    }
}