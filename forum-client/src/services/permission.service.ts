import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { PermissionDTO } from "../api-interfaces/dtos/permission.dto";
import { Observable } from "rxjs";
import { ApiResponse } from "../api-interfaces/responses/api.response";
import { PageResponse } from "../api-interfaces/responses/page.response";
import { T } from "@angular/cdk/keycodes";

@Injectable({
        providedIn: 'root'
})
export class PermissionService{
    private readonly http = inject(HttpClient)
    private readonly url = "/api/permission"

    savePermission(permissionDTO: PermissionDTO, permissionId: string | null): Observable<ApiResponse<PermissionDTO>>{
        if(permissionId){
            return this.http.put<ApiResponse<PermissionDTO>>(`${this.url}/edit/${permissionId}`, permissionDTO)
        }else{
            return this.http.post<ApiResponse<PermissionDTO>>(`${this.url}/create`, permissionDTO)
        }
    }

    listPermission(pageSize: number, pageNumber: number): Observable<PageResponse<PermissionDTO>> {
        const params = new HttpParams()
        .set("pageNumber", pageNumber)
        .set("pageSize", pageSize)
        return this.http.get<PageResponse<PermissionDTO>>(`${this.url}/list`, { params })
    }

    getPermission(permissionId: string) {
      return this.http.get<ApiResponse<PermissionDTO>>(`${this.url}/${permissionId}`)
    }
}