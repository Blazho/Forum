import { inject, Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { PageResponse } from "../api-interfaces/responses/page.response";
import { HttpClient } from "@angular/common/http";
import { PostRequest } from "../api-interfaces/requests/post.request";
import { Post } from "../api-interfaces/dtos/post.dto";

@Injectable({
        providedIn: 'root'
})
export class HandlebarService {

    private readonly http = inject(HttpClient)
    private readonly url = "/api/post"

    getPosts(threadId: number, pageSize: number, pageNumber: number): Observable<PageResponse<Post>>{
        const postRequest: PostRequest = {
            threadId: threadId,
            pageSize: pageSize,
            pageNumber: pageNumber
        }
        
        return this.http.post<PageResponse<Post>>(this.url, postRequest);
    }
}