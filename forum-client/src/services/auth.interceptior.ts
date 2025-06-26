import { HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { AuthService } from "./auth.service";

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn) => {
    const authService = inject(AuthService);
    const authToken = authService.getAuthToken(); // Get the token from your AuthService

    if (authToken) {
        // Clone the request to add the Authorization header
        const clonedReq = req.clone({
            setHeaders: {
                Authorization: `Bearer ${authToken}`
            }
        });
        return next(clonedReq);
    }

    return next(req); // If no token, proceed with the original request
};

