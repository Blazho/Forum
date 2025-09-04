import { inject, Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "./auth.service";
import { PermissionLayer, PermissionName } from "../api-interfaces/responses/login.response";

@Injectable({
    providedIn: 'root'
})
export class PermissionGuard implements CanActivate{

    private readonly authService = inject(AuthService)
    private readonly router = inject(Router)

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        const requiredPermission = route.data["permission"] as [{permission: PermissionName, layer: PermissionLayer}] | undefined
        console.log("Required permission for route", requiredPermission);
        
        if(!requiredPermission){
            console.warn("No permission defined for route, allowing access by default for route: " + state.url);
            return true
        }
        for(const {permission, layer} of requiredPermission){
            if(this.authService.hasPermission(permission, layer)){
                return true
            }
        }

        
        console.warn(`Access denied - Missing permission: ${JSON.stringify(requiredPermission)}`);
        this.router.navigate(['/no-access'])
        return false
        
    }
    
}
