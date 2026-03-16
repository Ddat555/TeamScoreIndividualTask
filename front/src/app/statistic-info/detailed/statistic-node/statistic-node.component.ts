import { Component, Input } from '@angular/core';
import { AbstractStatistic } from '../../../models/statistic.models';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-statistic-node',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './statistic-node.component.html',
  styleUrl: './statistic-node.component.css'
})
export class StatisticNodeComponent {
  @Input() statistic!: AbstractStatistic & { percentage?: number, color?: string };
  @Input() showPercentage: boolean = false;
  @Input() showColor: boolean = false;
  @Input() index?: number; 
}
