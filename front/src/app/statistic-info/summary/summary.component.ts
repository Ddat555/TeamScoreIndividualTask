import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryStatistic, SummaryStatistic } from '../../models/statistic.models';

@Component({
  selector: 'app-summary',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './summary.component.html',
  styleUrl: './summary.component.css'
})
export class SummaryComponent {
  @Input() summary: SummaryStatistic | null = null;
  @Input() categories: CategoryStatistic[] = [];
}
