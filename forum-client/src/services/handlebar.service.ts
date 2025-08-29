import { inject, Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { PageResponse } from "../api-interfaces/responses/page.response";
import { HttpClient } from "@angular/common/http";
import { PostRequest } from "../api-interfaces/requests/post.request";
import { PostDTO } from "../api-interfaces/dtos/post.dto";

@Injectable({
        providedIn: 'root'
})
//Need to be moved to post service
export class HandlebarService {

    private readonly http = inject(HttpClient)
    private readonly url = "/api/post"

    getPosts(threadId: number, pageSize: number, pageNumber: number): Observable<PageResponse<PostDTO>>{
        const postRequest: PostRequest = {
            threadId: threadId,
            pageSize: pageSize,
            pageNumber: pageNumber
        }
        
        return this.http.post<PageResponse<PostDTO>>(this.url, postRequest);
    }
}