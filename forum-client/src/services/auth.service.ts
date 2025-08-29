import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { RegisterRequest } from "../api-interfaces/requests/register.request";
import { LogInRequest } from "../api-interfaces/requests/login.request";
import { ApiResponse } from "../api-interfaces/responses/api.response";
import { LoginResponse, PermissionDictionary } from "../api-interfaces/responses/login.response";

@Injectable({
    providedIn: 'root'
})
export class AuthService{
    private path = '/api/auth'
    private http = inject(HttpClient)
    private readonly AUTH_TOKEN_NAME = "AUTH_TOKEN"
    private permissions: PermissionDictionary = {}

    constructor(){
        const storedPermissions = sessionStorage.getItem("USER_PERMISSIONS")
        if(storedPermissions){
            this.permissions = JSON.parse(storedPermissions)
        }
    }

    getPermissions(): PermissionDictionary{
        return this.permissions
    }

    setPermissions(permissions: PermissionDictionary): void{
        this.permissions = permissions
        sessionStorage.setItem("USER_PERMISSIONS", JSON.stringify(permissions))
    }

    clearPermissions(): void{
        this.permissions = {}
        sessionStorage.removeItem("USER_PERMISSIONS")
    }

    hasPermission(resource: string, requiredLayer: number): boolean{
        const userLayer = this.permissions[resource] ?? 0
        return userLayer >= requiredLayer
    }

    login(logInRequest: LogInRequest): Observable<ApiResponse<LoginResponse>>{
        return this.http.post<ApiResponse<LoginResponse>>(`${this.path}/login`, logInRequest).pipe(
            tap({
                next: response => {
                    response.data && this.setPermissions(response.data.userPermissions)
                    sessionStorage.setItem(this.AUTH_TOKEN_NAME, response.data.token)
                }
            })
        )
    }

    register(registerObject: RegisterRequest) : Observable<ApiResponse<string>> {
        return this.http.post<ApiResponse<string>>(`${this.path}/register`, registerObject)
    }

    getAuthToken(): string | null{
        return sessionStorage.getItem(this.AUTH_TOKEN_NAME)
    }
}
