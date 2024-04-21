#include <iostream>
#include <vector>
#include <map> 
#include <set> 
#include <algorithm>

struct Tree
{
	int x;
	int y;
	int age;

	bool operator<(const Tree& other) const {
		return age > other.age;
	}
};

std::vector<std::vector<int>> addedNourishmentMap;
std::vector<std::vector<int>> nourishmentMap;
std::vector<Tree> trees;

void applySpring(std::vector<Tree>& dieTrees)
{
	std::vector<Tree> newTrees;

	std::sort(trees.begin(), trees.end());
	for (int i = trees.size() - 1; i >= 0; i--) {
		const int age = trees[i].age;
		const int x = trees[i].x;
		const int y = trees[i].y;

		if (nourishmentMap[x][y] - age >= 0)
		{
			nourishmentMap[x][y] -= age;
			++trees[i].age;
			newTrees.emplace_back(trees[i]);
		}
		else
		{
			dieTrees.emplace_back(trees[i]);
		}
	}

	trees = newTrees;
}

void applySummer(std::vector<Tree>& dieTrees)
{
	for (auto& tree : dieTrees)
	{
		const int age = tree.age;
		const int x = tree.x;
		const int y = tree.y;

		nourishmentMap[x][y] += age / 2;
	}
}

bool isValidRange(int row, int column)
{
	if (row <= 0 || column <= 0 || row >= nourishmentMap.size() || column >= nourishmentMap.size())
	{
		return false;
	}
	return true;
}

void applyAutumn()
{
	static std::vector<std::pair<int, int>> direction = { std::make_pair(-1, -1),	std::make_pair(-1, 0),	std::make_pair(-1, 1), 
														  std::make_pair(0, -1),	std::make_pair(0, 1),	
														  std::make_pair(1, -1),	std::make_pair(1, 0),	std::make_pair(1, 1) };

	std::vector<Tree> newTrees;
	newTrees.reserve(trees.size() * 8);

	for (int i = trees.size() - 1; i >= 0; i--) 
	{
		if (trees[i].age % 5 != 0)
		{
			continue;
		}
		int row = trees[i].x;
		int column = trees[i].y;
		
		for (const auto &[rowDelta, columnDelta] : direction)
		{
			int targetRow = row + rowDelta;
			int targetColumn = column + columnDelta;

			if (!isValidRange(targetRow, targetColumn))
			{
				continue;
			}
			newTrees.emplace_back(Tree{targetRow, targetColumn, 1});
		}

	}
	for (const auto& tree : newTrees)
	{
		trees.emplace_back(tree);
	}
}

void applyWinter()
{
	for (int i = 0; i < nourishmentMap.size(); ++i)
	{
		for (int j = 0; j < nourishmentMap.size(); ++j)
		{
			nourishmentMap[i][j] += addedNourishmentMap[i][j];
		}
	}
}
void solve()
{
	std::vector<Tree> dieTrees;
	applySpring(dieTrees);
	applySummer(dieTrees);
	applyAutumn();
	applyWinter();
}

int main()
{
	int mapSize;
	int treeCount;
	int years;

	std::cin >> mapSize >> treeCount >> years;

	addedNourishmentMap.resize(mapSize + 1);
	for (auto& row : addedNourishmentMap)
	{
		row.resize(mapSize + 1);
	}

	for (int i = 1; i <= mapSize; ++i)
	{
		for (int j = 1; j <= mapSize; ++j)
		{
			std::cin >> addedNourishmentMap[i][j];
		}
	}

	nourishmentMap.resize(mapSize + 1);
	for (auto& row : nourishmentMap)
	{
		row.resize(mapSize + 1);
	}

	for (int i = 1; i <= mapSize; ++i)
	{
		for (int j = 1; j <= mapSize; ++j)
		{
			nourishmentMap[i][j] = 5;
		}
	}
	int row;
	int column;
	int age;
	for (int i = 0; i < treeCount; ++i)
	{
		std::cin >> row >> column >> age;
		trees.emplace_back(Tree{ row, column, age });
	}

	std::sort(trees.begin(), trees.end());

	while (years--)
	{
		solve();
		if (!trees.size())
		{
			break;
		}
	}
	std::cout << trees.size() << '\n';
}


