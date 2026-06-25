import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'home',
  standalone: true,
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})

export class Home {
  title = 'Willkommen!';
  constructor(public router: Router) {
    this.router = router;
  }
  navigateToOverview(): void {
    this.router.navigate(['/overview']);
  }
}
