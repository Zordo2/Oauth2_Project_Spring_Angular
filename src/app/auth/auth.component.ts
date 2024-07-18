import { take } from 'rxjs';
import { AuthService } from './../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  constructor(private authService:AuthService , private activateRoute: ActivatedRoute , private router: Router) {
    this.getAuthorizationCode();
   }

  ngOnInit(): void {
    this.authService.getToken().pipe(take(1)).subscribe(() => {
      this.authService.getToken().pipe(take(1)).subscribe((tokens) => {
        if((tokens as any)?.id_token){
          sessionStorage.setItem('id_token', (tokens as any).id_token);
          sessionStorage.setItem('refresh_token', (tokens as any).refresh_token);
          this.router.navigate(['/home']);
        }
      });
    });
  }
  getAuthorizationCode() {
    console.log("Zordok");
    this.activateRoute.queryParams.subscribe((params) => {
      if(params?.['code']){
        this.authService.code = params['code'];
        console.log('code=======',this.authService.code);
      }
    })
  }

}
