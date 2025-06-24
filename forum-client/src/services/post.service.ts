import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { PostDTO } from "../api-interfaces/dtos/post.dto";
import { Observable } from "rxjs";
import { ApiResponse } from "../api-interfaces/responses/api.response";
import { ThreadDTO } from "../api-interfaces/dtos/thread.dto";

@Injectable({
    providedIn: 'root'
})
export class PostService{
    private readonly http = inject(HttpClient)
    private readonly url = "/api/post"

    createPost(postDTO: PostDTO, postId: string | null): Observable<ApiResponse<PostDTO>>{
        if(postId){
            return this.http.put<ApiResponse<PostDTO>>(`${this.url}/edit/${postId}`, postDTO)
        }else{
            return this.http.post<ApiResponse<PostDTO>>(`${this.url}/create`, postDTO)
        }
    }

    getPost(postId: string): Observable<ApiResponse<PostDTO>> {
      return this.http.get<ApiResponse<PostDTO>>(`${this.url}/${[postId]}`)
    }
}