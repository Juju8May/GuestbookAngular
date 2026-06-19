import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'home-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})

export class Home {
  title = 'Willkommen zum Gästebuch';
  constructor(public router: Router) {
    this.router = router;
  }
  navigateToOverview(): void {
    this.router.navigate(['/overview']);
  }
}
