import { Component, Input } from '@angular/core';
import { Badge } from '../shared/Badge'

@Component({
  selector: 'app-badges',
  templateUrl: './badges.component.html',
  styleUrls: ['./badges.component.css']
})
export class BadgesComponent {

  constructor() { }

  @Input()
  badges: Badge[]
}
