#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "running_time_survey.h"

Task task_list[] = {
    {"Linear Time Test", linear_time, 10000000, 0},
    // {"Nlogn Time Test", nlogn_time, 1000000, 0},
    // {"Quadratic Time Test", quadratic_time, 100000, 0},
    // {"Cubic Time Test", cubic_time, 1000, 0},
    // {"Exponential Time Test", exponential_time, 29, 1},
    // {"Factorial Time Test", factorial_time, 12, 1}
};

clock_t start, finish;

int main()
{
    FILE *file = fopen("c_result.csv", "w");
    srand(time(NULL));

    int task_num = sizeof(task_list) / sizeof(task_list[0]);
    int type_flag = -1;
    for (int i = 0; i < task_num; i++)
    {
        Task t = task_list[i];
        printf("running: %s\n", t.task_name);

        if (t.type == 0)
        {
            // normal test

            // table header
            if (type_flag != t.type)
            {
                type_flag = t.type;
                for (int n = 10; n <= t.upperBound; n *= 10)
                    fprintf(file, ",n=%d", n);
                fprintf(file, "\n");
            }
            // start test
            fprintf(file, "%s", t.task_name);
            for (int n = 10; n <= t.upperBound; n *= 10)
            {
                ll result = t.function(n);
                fprintf(file, ",%dms", result);
            }
            fprintf(file, "\n");
        }
        else
        {
            // Exponential or FactorialTest Test

            // table header
            if (type_flag != t.type)
            {
                type_flag = t.type;
                for (int n = 10; n <= t.upperBound; n += 1)
                    fprintf(file, ",n=%d", n);
                fprintf(file, "\n");
            }
            // start test
            fprintf(file, "%s", t.task_name);
            for (int n = 10; n <= t.upperBound; n += 1)
            {
                ll result = t.function(n);
                fprintf(file, ",%dms", result);
            }
            fprintf(file, "\n");
        }
    }
    fclose(file);
}

ll linear_time(int n)
{
    int *array = malloc(n * sizeof(int));
    generate_array(array, n);
    start = clock();
    int max_int;
    for (int i = 0; i < n; i++)
        if (array[i] > max_int)
            max_int = array[i];
    finish = clock();
    free(array);
    return finish - start;
}

ll nlogn_time(int n)
{
    // TODO: generate your test input data here
    start = clock();
    // TODO: write an algorithm
    finish = clock();
    // Remember to free the malloc data
    return finish - start;
}

ll quadratic_time(int n)
{
    // TODO: generate your test input data here
    start = clock();
    // TODO: write an algorithm
    finish = clock();
    // Remember to free the malloc data
    return finish - start;
}

ll cubic_time(int n)
{
    // TODO: generate your test input data here
    start = clock();
    // TODO: write an algorithm
    finish = clock();
    // Remember to free the malloc data
    return finish - start;
}

ll exponential_time(int n)
{
    // TODO: generate your test input data here
    start = clock();
    // TODO: write an algorithm
    finish = clock();
    // Remember to free the malloc data
    return finish - start;
}

ll factorial_time(int n)
{
    // TODO: generate your test input data here
    start = clock();
    // TODO: write an algorithm
    finish = clock();
    // Remember to free the malloc data
    return finish - start;
}

void generate_array(int *array, int n)
{
    for (int i = 0; i < n; i++)
        array[i] = rand();
}