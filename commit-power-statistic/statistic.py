import os
import platform

SEPARATOR = '\\' if platform.system() == 'Windows' else '/'


class ProjectInfo(object):

    def __init__(self, path: str, users=None):
        """
        :param users: 正则匹配 --author参数，比如 Jimo\|Hehe
        """
        self.path = path
        self.users = users
        self.name = path[path.rindex(SEPARATOR) + 1:]


def read_commit(p):
    """
    读取一个项目的git commit记录
    :return: list,每条commit信息
    """
    cmd = 'cd {} && git log --oneline'.format(p.path)
    if p.users:
        cmd += ' --author="{}"'.format(p.users)
    with os.popen(cmd, 'r') as f:
        d = f.buffer.read().decode(encoding='utf-8')
        return d.split('\n')


# 新增功能的关键字
new_feature_keywords = ['新增', '增加', '加入']
fix_bug_keywords = ['bug', '修改', '修复']


def is_new_feature(c):
    for k in new_feature_keywords:
        if k in c:
            return True
    return False


def is_fix(c):
    for k in fix_bug_keywords:
        if k in c:
            return True
    return False


def statistic_project(p):
    """
    统计每个项目提交数量，新功能和修改bug的数量
    :param p:
    :return:
    """
    commits = read_commit(p)
    stat = {
        'feature': 0,
        'fix': 0,
        'other': 0
    }
    for c in commits:
        if is_new_feature(c):
            stat['feature'] += 1
        elif is_fix(c):
            stat['fix'] += 1
        else:
            stat['other'] += 1
    return stat


def statistic_all(projects):
    """
    统计所有项目情况，做一个汇总
    :param projects: list,项目目录列表
    """
    stat_all = {
        'feature': 0,
        'fix': 0,
        'other': 0
    }
    print('| 项目 | 新增功能 | 修改BUG | 维护提交 |')
    print('| --- | --- | --- | --- |')
    for p in projects:
        stat = statistic_project(p)
        print('| {} | {} | {} | {} |'.format(p.name, stat['feature'], stat['fix'], stat['other']))
        stat_all['feature'] += stat['feature']
        stat_all['fix'] += stat['fix']
        stat_all['other'] += stat['other']
    print('| 总计{}个 | {} | {} | {} |'.format(len(projects), stat_all['feature'], stat_all['fix'], stat_all['other']))


if __name__ == '__main__':
    projects_ = [
        ProjectInfo('D:\\workspace\\just-ts'),
        ProjectInfo('D:\\workspace\\just-portal'),
        ProjectInfo('D:\\workspace\\just-driver-web'),
        ProjectInfo('D:\\workspace\\just-db-driver-java'),
        ProjectInfo('D:\\workspace\\just-db-driver-python'),
        ProjectInfo('D:\\workspace\\just-driver-java'),
        ProjectInfo('D:\\workspace\\just-driver-python'),
        ProjectInfo('D:\\workspace\\just-home-backend'),
        ProjectInfo('D:\\workspace\\just-cmd-cli'),
        ProjectInfo('D:\\workspace\\demo\\jimo-dev-deploy'),
        ProjectInfo('D:\\workspace\\jimo-tools'),
        ProjectInfo('D:\\workspace\\just-admin'),
        ProjectInfo('D:\\workspace\\just-admin-web'),
        ProjectInfo('D:\\workspace\\jsls'),
        ProjectInfo('D:\\workspace\\uc-geomesa-pioneer', users='wangpeng417'),
    ]
    statistic_all(projects_)
