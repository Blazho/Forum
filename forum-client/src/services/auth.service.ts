import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { RegisterRequest } from "../api-interfaces/requests/register.request";
import { LogInRequest } from "../api-interfaces/requests/login.request";
import { ApiResponse } from "../api-interfaces/responses/api.response";
import { LoginResponse, PermissionDictionary, PermissionLayer, PermissionName } from "../api-interfaces/responses/login.response";
import {jwtDecode} from "jwt-decode";

interface JwtPayload {
  sub: string;        // username
  exp: number;        // expiration time (as UNIX timestamp)
  userId?: number;    
}

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
        if(storedPermissions && sessionStorage.getItem(this.AUTH_TOKEN_NAME) != null){
            this.permissions = JSON.parse(storedPermissions)            
        }else{
            this.addAnonymousePermissions()
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
        this.addAnonymousePermissions()
    }

    hasPermission(resource: PermissionName, requiredLayer: PermissionLayer): boolean{
        const userLayer: PermissionLayer = this.permissions[resource] ?? PermissionLayer.NONE
        return userLayer >= requiredLayer
    }

    login(logInRequest: LogInRequest): Observable<ApiResponse<LoginResponse>>{
        return this.http.post<ApiResponse<LoginResponse>>(`${this.path}/login`, logInRequest).pipe(
            tap({
                next: response => {
                    if(response.data){
                        this.setPermissions(response.data.userPermissions)
                    } 
                    
                    sessionStorage.setItem(this.AUTH_TOKEN_NAME, response.data.token)
                }
            })
        )
    }

    clearUserData() {
        sessionStorage.removeItem(this.AUTH_TOKEN_NAME);
        this.clearPermissions()
        sessionStorage.clear()
    }

    register(registerObject: RegisterRequest) : Observable<ApiResponse<string>> {
        return this.http.post<ApiResponse<string>>(`${this.path}/register`, registerObject)
    }

    getAuthToken(): string | null{
        return sessionStorage.getItem(this.AUTH_TOKEN_NAME)
    }

    getUserId(): number | null{
        const token = this.getAuthToken();
        if (!token) return null;

        try {
            const decoded = jwtDecode<JwtPayload>(token);
            return decoded.userId ?? null;
        } catch (e) {
            console.error("Failed to decode token", e);
            return null;
        }
    }

    private addAnonymousePermissions(){
        const permissions: PermissionDictionary = {}
        permissions[PermissionName.PROMOTE_USER_PERMISSION] = PermissionLayer.NONE
        permissions[PermissionName.POST_PERMISSION] = PermissionLayer.VIEW
        permissions[PermissionName.THREAD_PARENT_PERMISSION] = PermissionLayer.VIEW
        permissions[PermissionName.THREAD_CHILD_PERMISSION] = PermissionLayer.VIEW
        this.permissions = permissions
    }
}
