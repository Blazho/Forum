import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { RegisterRequest } from "../api-interfaces/requests/register.request";
import { LogInRequest } from "../api-interfaces/requests/login.request";
import { ApiResponse } from "../api-interfaces/responses/api.response";

@Injectable({
    providedIn: 'root'
})
export class AuthService{
    private path = '/api/auth'
    private http = inject(HttpClient)
    private readonly AUTH_TOKEN_NAME = "AUTH_TOKEN"

    login(logInRequest: LogInRequest): Observable<ApiResponse<string>>{
        return this.http.post<ApiResponse<string>>(`${this.path}/login`, logInRequest).pipe(
            tap({
                next: tokenResponse => {
                    sessionStorage.setItem(this.AUTH_TOKEN_NAME, tokenResponse.data)
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