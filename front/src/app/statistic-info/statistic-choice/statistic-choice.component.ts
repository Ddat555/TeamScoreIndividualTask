import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

export enum StatisticType{
  Categories,
  Payments,
  Sellers
}

export interface SearchParams {
  period: string,
  startDate?: string,
  endDate?: string,
  type: StatisticType
}

@Component({
  selector: 'app-statistic-choice',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './statistic-choice.component.html',
  styleUrl: './statistic-choice.component.css'
})
export class StatisticChoiceComponent {
  @Output() search = new EventEmitter<SearchParams>();

  selectedPeriod = '7days';
  startDate: string = this.getDefaultStartDate();
  endDate: string = this.getCurrentDate();
  selectedType: StatisticType = StatisticType.Categories;



  getCurrentDate(): string {
    const today = new Date();
    return today.toISOString().split('T')[0];
  }
  
  getDefaultStartDate(): string {
    const date = new Date();
    date.setDate(date.getDate() - 7);
    return date.toISOString().split('T')[0];
  }
  
  onSearch() {
    if (this.selectedPeriod === 'custom') {
      this.search.emit({
        period: 'custom',
        startDate: this.startDate,
        endDate: this.endDate,
        type: this.selectedType
      });
    } else {
      this.search.emit({
        period: this.selectedPeriod,
        type: this.selectedType
      });
    }
  }

}
