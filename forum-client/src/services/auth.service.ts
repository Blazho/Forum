import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { RegisterRequest } from "../api-interfaces/requests/register.request";

@Injectable({
    providedIn: 'root'
})
export class AuthService{
    private path = '/api/auth'
    private http = inject(HttpClient)

    login(username: string, password: string): any{
        //todo
    }

    register(registerObject: RegisterRequest) : Observable<string> {
        return this.http.post<string>(`${this.path}/register`, registerObject)
    }
}